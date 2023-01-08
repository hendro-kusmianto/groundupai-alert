package org.alert.services.dao;

import lombok.extern.slf4j.Slf4j;
import org.alert.domain.entities.Alert;
import org.alert.domain.exceptions.NonExistentEntityException;
import org.alert.domain.forms.QueryForm;
import org.alert.utils.OnBlank;
import org.alert.utils.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Validated
@Slf4j
@Service
public class AlertService {
    @Autowired
    private ReactiveMongoTemplate template;

    public Mono<Alert> findById(String id) {
        return template.findById(id, Alert.class)
                .switchIfEmpty(Mono.error(new NonExistentEntityException()))
                .doOnSuccess(a -> log.info("Alert `id={}, machine={}, anomaly={}, timestamp=`", a.id(), a.machine()));
    }

    public Mono<Alert> save(@Valid Alert data) {
        return findById(data.id())
                .map(a -> data)
                .onErrorResume(NonExistentEntityException.class, e -> {
                    if (OnBlank.of(data.id()).isBlank()) {
                        return Mono.just(data);
                    }

                    return Mono.error(e);
                })
                .flatMap(doc -> template.save(doc))
//                .onErrorResume(DuplicateKeyException.class, e ->  Exceptions.propagateDuplicateKeyException(e.getMessage()))
                .doOnSuccess(a -> log.info("Data [machine={}, anomaly={}] disimpan", a.machine(), a.anomaly()));
    }

    public Flux<Alert> list(QueryForm form) {
        int page = form.getPage();
        int maxRow = form.getMaxRow();
        String sort = form.getSort();
        Sort.Direction sortOrder = form.getSortOrder();

        Query query = new Query(QueryUtils.toCriteria(form.getFilters()));
        if (maxRow > 0) {
            query.with(Pageable.ofSize(maxRow).withPage(page - 1));
        }
        if (sort != null) {
            query.with(Sort.by(sortOrder, sort));
        }

        return template.find(query, Alert.class);
    }

    public Mono<Long> count() {
        return template.estimatedCount(Alert.class);
    }

    public Mono<Boolean> delete(Alert a) {
        return template.remove(a)
                .doOnSuccess(result -> {
                    log.info("{} data has been deleted", result.getDeletedCount());
                })
                .map(deleteResult -> deleteResult.getDeletedCount() == 0 ? false : true);
    }

    public Mono<Alert> deleteById(String id) {
        return findById(id)
                .flatMap(p -> delete(p).map(aBoolean -> aBoolean ? p : null))
                .doOnSuccess(p -> {
                    if (p == null) {
                        log.info("No data has been deleted.");

                    } else {
                        log.info("`{}` has been deleted", p.machine());
                    }
                });
    }


    public Flux<Alert> deleteAll() {
        return template.remove(Alert.class)
                .findAndRemove();
    }
}
