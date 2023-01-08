import React, {ReactNode} from 'react';
import {Skeleton as PrimeSkeleton} from "primereact/skeleton";

export type SkeletonProps = {
    loading: boolean,
    shape?: 'rectangle' | 'circle',
    height?: string,
    minWidth?: number,
    maxWidth?: number,
    width?: number | string,
    style?: object,
    className?: string,
    children?: ReactNode;
};


function getRandomIntInclusive(min: number, max: number) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min + 1) + min); // The maximum is inclusive and the minimum is inclusive
}

export const Skeleton = ({
                             loading,
                             shape = 'rectangle',
                             height = '1rem',
                             minWidth = 0,
                             maxWidth = 100,
                             width,
                             style,
                             className,
                             children
                         }: SkeletonProps) => {

    const MySekeleton = () => {
        let calcWidth = '100%';
        if (width !== undefined) {
            calcWidth = typeof width === 'number' ? (width + '%') : width;
        } else {
            calcWidth = getRandomIntInclusive(minWidth, maxWidth) + '%';
        }

        // @ts-ignore
        const Comp = <PrimeSkeleton shape={shape} height={height} width={calcWidth}/>;

        // @ts-ignore
        return (loading && <div style={style} className={className}>{Comp}</div>)
    }

    // @ts-ignore
    return <><MySekeleton/>
        {!loading && children}
    </>;
}
