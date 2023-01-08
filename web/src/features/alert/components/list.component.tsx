import {ListBox} from "primereact/listbox";
import {useRef, useState} from "react";
import {TagAnomaly} from "./tag_anomaly.component";
import classNames from "classnames";
import {useQueryAlert} from "../useQueryAlert.hook";
import {Skeleton} from "./skeleton.component";
import {InputMask} from "primereact/inputmask";
import {Toast} from "primereact/toast";
import {pad2Digit} from "../pad2Digit";
import {TypeAlert} from "../alert.page";

type ListProps = {
    selected: any,
    onItemSelected: (data: any) => void
}

export const List = ({selected, onItemSelected}: ListProps) => {
    const refToast: any = useRef();
    const [filter, setFilter] = useState({});
    let {data = {}, isPlaceholderData, isLoading} = useQueryAlert(filter);
    const {daftar = []} = data;

    const itemTemplate = (option: any) => {
        const {err_id, machine, anomaly, reason, timestamp} = option as TypeAlert;
        const cx = classNames('list-item', {
            'selected': err_id && err_id === selected?.err_id
        });

        const date = new Date(timestamp * 1000);
        let fullYear = date.getFullYear();
        let month = pad2Digit(date.getMonth() + 1);
        let dat = pad2Digit(date.getDate());
        let hours = pad2Digit(date.getHours());
        let minutes = pad2Digit(date.getMinutes());
        let second = pad2Digit(date.getSeconds());
        let formattedTimestamp = `${fullYear}-${month}-${dat} ${hours}:${minutes}:${second}`;
        option.timestampFormatted = formattedTimestamp

        let loading = isPlaceholderData || isLoading;
        return (
            <div className={cx}>
                <div className={'flex justify-content-between align-items-center'}>
                    <Skeleton loading={loading} width={'100px'}>
                        <span>ID #{err_id}  </span>
                    </Skeleton>
                    <Skeleton loading={loading} width={'50px'}>
                        <TagAnomaly rounded value={anomaly?.toLowerCase()}/>
                    </Skeleton>
                </div>
                <div className={'font-bold'}>
                    <Skeleton loading={loading} className={'mt-2'}>
                        {reason}
                    </Skeleton>
                </div>

                <div>
                    <Skeleton loading={loading} className={'mt-1'}>
                        Detected at {formattedTimestamp}
                    </Skeleton>
                </div>

                <div className={'machine'}>
                    <Skeleton loading={loading} className={'mt-3'}>{machine}</Skeleton>
                </div>
            </div>
        );
    }

    const FilterTemplate = () => {
        return <div className="flex flex-column">
            <label htmlFor="basic">Date :</label>
            <InputMask mask={'9999-99-99'}
                       placeholder={'yyyy-yy-yy'}
                       className={'p-inputtext-sm'}
                       onChange={(e) => {
                           if (e.value === '') {
                               setFilter({});
                           }
                       }}
                       onComplete={(e) => {
                           let start = new Date(e.value + "T00:00:00");
                           let end = new Date(e.value + "T23:00:00");

                           if (!isNaN(start.getTime()) && !isNaN(end.getTime())) {
                               setFilter((prev) => ({
                                   timestamp_start: 'timestamp;gte;' + (start.getTime() / 1000),
                                   timestamp_end: 'timestamp;lte;' + (end.getTime() / 1000)
                               }))
                           } else {
                               refToast.current.show({
                                   severity: 'error',
                                   detail: 'Invalid date format'
                               });
                           }

                       }}/>
        </div>
    }

    return <>
        <Toast ref={refToast}/>
        <ListBox value={selected}
                 filter
                 filterTemplate={FilterTemplate}
                 emptyMessage={"no data found"}
                 listStyle={{height: '100vh'}}
                 itemTemplate={itemTemplate}
                 options={daftar}
                 onChange={(e) => onItemSelected?.(e.value)}>
        </ListBox>
    </>
}
