import React from 'react';
import './app.css';

import {Title} from "./components";
import {Navbar} from "./layouts";
import {PageAlert} from "./features/alert/alert.page";

// import {ReactQueryDevtools} from "@tanstack/react-query-devtools";

function App() {
    return <>
        <Title title={"Monitoring"}/>
        <div className="layout-wrapper ">
            <Navbar/>
            <div className={'layout-content'}>
                <PageAlert/>
            </div>
        </div>
        {/*<ReactQueryDevtools initialIsOpen position={"top-right"}/>*/}

    </>
}

export default App;
