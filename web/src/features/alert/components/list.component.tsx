import {ListBox} from "primereact/listbox";
import {useState} from "react";
import {TagAnomaly} from "./tag_anomaly.component";
import classNames from "classnames";
import {useQueryAlert} from "./useQueryAlert.hook";
import {Skeleton} from "./skeleton.component";


export const List = () => {
    const [selected, setSelected] = useState();
    const [filter, setFilter] = useState({
        date: ''
    })
    let {data = {}, isPlaceholderData, isLoading} = useQueryAlert(filter);
    const {daftar = []} = data;

    const itemTemplate = (option: any) => {
        const {id, err_id, machine, anomaly, timestamp} = option;
        const cx = classNames('list-item', {
            'selected': err_id && err_id === selected
        });

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
                        Unknown Normally
                    </Skeleton>
                </div>

                <div>
                    <Skeleton loading={loading} className={'mt-1'}>
                        Detected at {timestamp}
                    </Skeleton>
                </div>

                <div className={'machine'}>
                    <Skeleton loading={loading} className={'mt-3'}>{machine}</Skeleton>
                </div>
            </div>
        );
    }

    return <ListBox value={selected}
                    emptyMessage={"no data found"}
                    listStyle={{height: '100vh'}}
                    itemTemplate={itemTemplate}
                    options={daftar}
                    onChange={(e) => setSelected(e.value?.err_id)}>
    </ListBox>
}
