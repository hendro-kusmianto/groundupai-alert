import {useMutation, useQueryClient} from "@tanstack/react-query";
import {saveAlert} from "../alert.service";
import {TypeAxiosError, TypeMutationCallback} from "../types";
import {Keys} from "../keys.constant";

export const useSaveAlert = (options: TypeMutationCallback = {}) => {
    let client = useQueryClient();
    const {onSuccess, onError, onSettled} = options;
    return useMutation(saveAlert, {
        onSuccess: (data) => {
            client.refetchQueries(Keys.all);
            onSuccess?.(data);
        },
        onError: (err: TypeAxiosError) => {
            if (err.response) {
                onError?.(err.response.data);
            }
        }
    });
}
