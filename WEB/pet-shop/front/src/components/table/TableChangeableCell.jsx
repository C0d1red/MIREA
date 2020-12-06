import TableCell from "@material-ui/core/TableCell";
import React from "react";
import TextField from "@material-ui/core/TextField";

const handleTextFieldChanged = (event, row, paramToChange) => {
    row[paramToChange] = event.target.value
}

export const TableChangeableCell = (props) => {
    const {row, name, inputProps} = props
    return (
        <TableCell align="center">
            <TextField
                name={name}
                variant="outlined"
                defaultValue={row[name]}
                InputProps={inputProps}
                onChange={(e) => handleTextFieldChanged(e, row, name)}
            />
        </TableCell>
    )
}
