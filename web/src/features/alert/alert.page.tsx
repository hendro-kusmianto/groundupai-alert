import {List} from "./components/list.component";
import {useEffect, useRef, useState} from "react";
import {Form} from "./components/form.component";
import ReactWaves from "@dschoon/react-waves";
import {getColorMap} from "./getColorMap";

export type TypeAlert = {
    id: string,
    err_id: string,
    machine: string,
    anomaly: string,
    reason?: string,
    action?: string,
    comments?: string,
    timestamp: number
    timestampFormatted?: string
    clip?: string
}


export const PageAlert = () => {
    const refAnomaly: any = useRef();
    const refNormal: any = useRef();

    const [selected, setSelected] = useState<TypeAlert>();

    useEffect(() => {
        if (selected?.clip) {
            refAnomaly.current.src = 'audio/anomaly-' + selected.clip;
        }
    }, [selected?.clip])


    return <div>
        <div className={'card grid'}>
            <div className={'col-3'} style={{borderRight: '.5px solid #A2AEBC'}}>
                <List selected={selected} onItemSelected={setSelected}/>
            </div>
            <div className={'col-9 content pl-4'}>
                {
                    selected && <div>
                        <div className={'heading mb-2'}>
                            <div className={'title'}>Alert ID #{selected?.err_id}</div>
                            <div className={'subtitle'}>Detected {selected?.timestampFormatted}</div>
                        </div>
                        <div className={'grid mb-4'}>
                            <div className={'col-6'}>
                                <span className={'block mb-1'}>Anomaly Machine Output</span>
                                <audio controls ref={refAnomaly}>
                                    <source src={'audio/anomaly-' + selected.clip} type={'audio/wav'}/>
                                </audio>
                                <div>
                                    <ReactWaves
                                        audioFile={'audio/anomaly-' + selected.clip}
                                        className={"react-waves"}
                                        options={{
                                            barHeight: 2,
                                            cursorWidth: 0,
                                            height: 144,
                                            hideScrollbar: true,
                                            progressColor: "#1f77b4",
                                            responsive: true,
                                            waveColor: "#1f77b4",
                                            mediaControls: true
                                        }}
                                        spectrogramOptions={{
                                            container: '#wave-spectrogram-anomaly',
                                            colorMap: getColorMap(),
                                            noverlap: true,
                                            labels: true,
                                            frequencyMax: 8000
                                        }}
                                    />
                                    <div id='wave-spectrogram-anomaly' style={{margin: '0 auto'}}/>
                                </div>
                            </div>
                            <div className={'col-6'}>
                                <span className={'block mb-1'}>Normal Machine Output</span>
                                <audio controls>
                                    <source src={'audio/normal-' + selected.clip} type={'audio/wav'}/>
                                </audio>
                                <div>
                                    <ReactWaves
                                        audioFile={'audio/normal-' + selected.clip}
                                        className={"react-waves"}
                                        options={{
                                            barHeight: 2,
                                            cursorWidth: 0,
                                            height: 144,
                                            hideScrollbar: true,
                                            progressColor: "#1f77b4",
                                            responsive: true,
                                            waveColor: "#1f77b4",
                                            mediaControls: true
                                        }}
                                        spectrogramOptions={{
                                            container: '#wave-spectrogram-normal',
                                            colorMap: getColorMap(),
                                            noverlap: true,
                                            labels: true,
                                            frequencyMax: 8000
                                        }}
                                    />
                                    <div id='wave-spectrogram-normal' style={{margin: '0 auto'}}/>
                                </div>
                            </div>
                        </div>

                        <Form alert={selected}/>
                    </div>
                }

            </div>
        </div>
    </div>
}
