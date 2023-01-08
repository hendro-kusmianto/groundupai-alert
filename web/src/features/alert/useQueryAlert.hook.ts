import {PlaceholderData} from "./placeholder.data";
import {useMemo} from "react";
import {useQuery} from "@tanstack/react-query";
import {queryAllAlerts} from "./alert.service";
import {Keys} from "./keys.constant";

export const useQueryAlert = ( filters :any) => {
    const placeholderData = useMemo(() => ({daftar: PlaceholderData}), [])
    return useQuery(Keys.list(filters),
        () => queryAllAlerts(filters),
        {
            placeholderData: placeholderData
        });

}
