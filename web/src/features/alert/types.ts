export type TypeResponseMeta = {
    code: string,
    message: string
}

export type TypeResponseData = {
    [index: string]: any,
    daftar?: []
}

export type TypeResponseErrorField = {
    field: string,
    err_code: string,
    err_message: string
}

export type TypeResponseEnvelope = {
    meta: TypeResponseMeta,
    data?: TypeResponseData,
    errors?: TypeResponseErrorField[]
}

export type TypeAxiosError = {
    response: {
        status: number,
        statusText: string,
        data: TypeResponseEnvelope
    }
}

export type TypeOnError = (err: TypeResponseEnvelope) => void
export type TypeOnSuccess = (data: any) => void
export type TypeOnSettled = (data: any) => void

export type TypeMutationCallback = {
    onSuccess?: TypeOnSuccess,
    onError?: TypeOnError,
    onSettled?: TypeOnSettled
}
