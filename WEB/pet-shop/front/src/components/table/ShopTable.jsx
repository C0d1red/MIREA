import React, { useState } from "react";
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableRow from '@material-ui/core/TableRow';
import { Button } from '@material-ui/core';
import {TableChangeableCell} from "./TableChangeableCell";
import TablePagination from "@material-ui/core/TablePagination";
import {useStyles} from "../../styles";
import Card from "@material-ui/core/Card";
import InputAdornment from "@material-ui/core/InputAdornment";

export const ShopTable = (props) => {
    const {rows, deletePetById, changePetById} = props;
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5);
    const classes = useStyles();

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(event.target.value);
        setPage(0);
    };

    return (
        <Card className={classes.card}>
            <TableContainer>
                <Table>
                    <TableRow className={classes.tableRow}>
                        <TableCell className={classes.idCell} align="center">Id</TableCell>
                        <TableCell className={classes.textCell} align="center">Name</TableCell>
                        <TableCell className={classes.textCell} align="center">Type</TableCell>
                        <TableCell className={classes.textCell} align="center">Cost</TableCell>
                        <TableCell className={classes.buttonCell} align="center">Buy</TableCell>
                        <TableCell className={classes.buttonCell} align="center">Change</TableCell>
                    </TableRow>
                    <TableBody className={classes.tableBody}>
                        {(rowsPerPage > 0 ?
                                rows.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                :
                                rows
                        ).map((row) => (
                            <TableRow className={classes.tableRow} key={row.id}>
                                <TableCell align="center">{row.id}</TableCell>
                                <TableChangeableCell row={row} name="name" />
                                <TableChangeableCell row={row} name="type" />
                                <TableChangeableCell
                                    row={row}
                                    name="cost"
                                    inputProps={{startAdornment: <InputAdornment position="start">$</InputAdornment>}}
                                />
                                <TableCell align="center">
                                    <Button variant="contained" onClick={() => deletePetById(row.id)}>Buy</Button>
                                </TableCell>
                                <TableCell align="center">
                                    <Button variant="contained" onClick={() => changePetById(row.id)}>Change</Button>
                                </TableCell>
                            </TableRow>
                        ))}
                        {[...Array(rowsPerPage - Math.min(rowsPerPage, rows.length - page * rowsPerPage))]
                            .map(() => <TableRow className={classes.tableRow}/> )
                        }
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                rowsPerPageOptions={[5, 10, 25]}
                count={rows.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onChangePage={handleChangePage}
                onChangeRowsPerPage={handleChangeRowsPerPage}
            />
        </Card>
    );
}
