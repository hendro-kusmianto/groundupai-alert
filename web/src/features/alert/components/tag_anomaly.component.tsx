import styled from "styled-components";
import {Tag} from "primereact/tag";

export const TagAnomaly = styled(Tag)`
  height: 20px;
  background-color: ${({value = ''}) => {
    let color = 'lightgray';
    switch (value) {
      case "severe" :
        color = 'red';
        break;
      case "moderate" :
        color = '#FCA034';
        break;
      case "mild" :
        color = 'green';
        break;
    }
    return color;
  }};
`
