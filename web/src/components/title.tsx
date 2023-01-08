import React from "react";

import {Helmet} from "react-helmet";

export const Title = ({title = "tes"}: { title?: string }) => {
    return <Helmet title={title + " ~ groundup.ai" }/>
}
