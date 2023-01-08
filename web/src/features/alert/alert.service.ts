import axios from "axios";
import {EndPoint} from "./endpoint.constant";
import {TypeResponseData} from "./types";

let controller: any = null;
export const queryAllAlerts = (params: any) => {
    controller?.abort();
    controller = new AbortController();
    let signal = controller.signal;
    return axios.get(EndPoint.alert.base, {
        signal,
        params
    }).then((resp) => resp.data.data || {}) as Promise<TypeResponseData>;
}

export const saveAlert = (data: any) => {
    controller?.abort();
    controller = new AbortController();
    let signal = controller.signal;

    let promise = axios.put(EndPoint.alert.self(data.id), data, {signal});
    return promise.then((resp) => resp.data.data || {}) as Promise<TypeResponseData>;
}
