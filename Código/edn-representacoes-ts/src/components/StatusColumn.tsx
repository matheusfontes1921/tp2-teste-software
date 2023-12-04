import { StatusType } from "../shared/types/StatusType";
import { Tag } from "antd";
 
interface StatusColumnProps {
    status?: StatusType;
}

const colors: string[] = [
    "green",
    "grey",
    "red",
    "blue",
]
const StatusColumn = ({status}: StatusColumnProps) => {
    if(!status) {
        return null;
    } 
    const currentColor = colors[status.id] || colors[0];
    return (
         <Tag color={currentColor}>{status.status}</Tag>
    )


}
export default StatusColumn;