import {Controller, useForm} from "react-hook-form";
import {Button} from "primereact/button";
import {InputTextarea} from "primereact/inputtextarea";
import {TypeAlert} from "../alert.page";
import {Dropdown} from "primereact/dropdown";
import {useEffect, useRef} from "react";
import {useSaveAlert} from "./useSaveAlert.hook";
import {Toast} from "primereact/toast";

export const Form = ({alert}: { alert: TypeAlert }) => {
    const refToast: any = useRef();
    const {handleSubmit, control, setValue} = useForm({
        defaultValues: {
            id: '',
            reason: '',
            action: '',
            comments: ''
        }
    });

    useEffect(() => {
        if (alert) {
            setValue("id", alert?.id || '');
            setValue("reason", alert?.reason || '');
            setValue("action", alert?.action || '');
            setValue("comments", alert?.comments || '');
        }
    }, [alert])

    let {mutate: save, isLoading} = useSaveAlert({
        onSuccess: () => {
            refToast.current.show({
                severity: 'success',
                detail: 'Saved'
            });
        },
        onError: (err) => {

        }
    });

    const onSubmit = (data: any) => {
        save(data)
    }

    const reasonOption = {
        "cnc machine": [
            "Unknown Anomally",
            "Manufacturing Error",
            "Overheating",
            "Positioning Error",
            "Thermal Deformation",
            "Adjusment Error",
            "Measurement Error",
            "Internal Stress"
        ],
        "milling machine": [
            "Unknown Anomally",
            "Chip Jamming",
            "Thermal Crack"
        ]
    }

    return <div className={'form'}>
        <Toast ref={refToast}/>
        <form onSubmit={handleSubmit(onSubmit)}>
            <div className="field">
                <label className="block">Equipment</label>
                <label className="block value">{alert.machine}</label>
            </div>
            <div className="field">
                <label htmlFor="reason" className="block">Suspected Reason</label>
                <Controller name="reason"
                            control={control}
                            render={({field}) => {
                                // @ts-ignore
                                return <Dropdown {...field} options={reasonOption[alert.machine.toLowerCase()]}/>
                            }}
                />
            </div>
            <div className="field">
                <label htmlFor="action" className="block">Action Required</label>
                <Controller name="action"
                            control={control}
                            render={({field}) => {
                                // @ts-ignore
                                return <Dropdown {...field} options={[
                                    "replace",
                                    "maintenance",
                                    "nothing"
                                ]}/>
                            }}
                />
            </div>
            <div className="field">
                <label htmlFor="comments" className="block">Comments</label>
                <Controller name="comments"
                            control={control}
                            render={({field}) => {
                                // @ts-ignore
                                return <InputTextarea {...field} className={'p-inputtext-sm w-full'} rows={5}/>;
                            }}
                />
            </div>

            <Button label={"Update"} loading={isLoading} style={{
                minWidth: 105,
                height: 35,
                backgroundColor: '#526CFE',
                borderColor: '#526CFE'
            }}/>
        </form>
    </div>
}
