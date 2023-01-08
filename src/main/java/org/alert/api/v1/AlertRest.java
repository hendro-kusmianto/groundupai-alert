package org.alert.api.v1;

import lombok.extern.slf4j.Slf4j;
import org.alert.domain.entities.Alert;
import org.alert.domain.forms.AlertForm;
import org.alert.domain.forms.AlertUpdateForm;
import org.alert.domain.model.ResponseEnvelope;
import org.alert.services.WrapperService;
import org.alert.services.dao.AlertService;
import org.alert.utils.QueryUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/alerts")
public class AlertRest {
    @Autowired
    private WrapperService wrapper;

    @Autowired
    private AlertService service;

    @PostMapping
    public Mono<ResponseEntity<ResponseEnvelope>> create(@RequestBody @Valid AlertForm form) {

        Alert alert = new Alert()
                .machine(form.getMachine())
                .anomaly(form.getAnomaly())
                .reason("Unknown Anomally")
                .sensor(form.getSensor())
                .clip(form.getClip())
                .timestamp(form.getTimestamp());

        return wrapper.response(envelope -> {
            return service.save(alert)
                    .doOnSuccess(a -> envelope.put("info", a.toDto()))
                    .thenReturn(true);
        });
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ResponseEnvelope>> update(@PathVariable String id, @RequestBody @Valid AlertUpdateForm form) {
        return wrapper.response(envelope -> {
            return service.findById(id)
                    .map(alert -> alert.reason(form.getReason()).action(form.getAction()).comments(form.getComments()))
                    .flatMap(p -> service.save(p))
                    .doOnSuccess(doc -> envelope.put("info", doc.toDto()))
                    .thenReturn(true);
        });
    }

    @GetMapping
    public Mono<ResponseEntity<ResponseEnvelope>> list(@RequestParam Map<String, String> map) {
        int page = MapUtils.getIntValue(map, "page");
        int maxRow = MapUtils.getIntValue(map, "max_row");

        return wrapper.response(envelope -> {
                    return QueryUtils.toQueryFields(map)
                            .flatMap(queryForm -> service.list(queryForm)
                                    .map(a -> a.toDto())
                                    .collectList())
                            .doOnSuccess(list -> {
                                envelope.put("page", page);
                                envelope.put("max_row", maxRow);
                                envelope.put("daftar", list);
                            })
                            .then(service.count())
                            .doOnSuccess(aLong -> envelope.put("total", aLong))
                            .doOnError(throwable -> {
                                throwable.printStackTrace();
                            })
                            .thenReturn(true);

                }
        );
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<ResponseEnvelope>> delete(@PathVariable String id) {
        return wrapper.response(envelope -> service.deleteById(id).thenReturn(true));
    }

    @DeleteMapping
    public Mono<ResponseEntity<ResponseEnvelope>> delete() {
        return wrapper.response(envelope -> service.deleteAll().then(Mono.just(true)));
    }
}
