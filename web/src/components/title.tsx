import React from "react";

import {Helmet} from "react-helmet";
import {useQueryClient} from "@tanstack/react-query";
import {Keys} from "../data/constant";
import {TypeConfigInit} from "../services";

export const Title = ({title = "tes"}: { title?: string }) => {
    let client = useQueryClient();
    let config = client.getQueryData(Keys.init) as TypeConfigInit;
    return <Helmet title={title + " ~ " + config?.app_name}/>
}
