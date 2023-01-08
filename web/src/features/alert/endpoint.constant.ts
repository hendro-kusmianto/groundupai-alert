const HOST = process.env.REACT_APP_HOST;
export const EndPoint = {
    alert: {
        base: HOST + "/v1/alerts",
        self: (id: string) => HOST + "/v1/alerts/" + id,
        privileges: HOST + "/v1/roles/privileges",
    },
}
