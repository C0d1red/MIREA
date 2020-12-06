import React, { useState, useEffect } from "react";
import { getPets, deletePet, editPet } from "../api/api"
import {Grid} from "@material-ui/core";
import {useStyles} from "../styles";
import {ShopTable} from "./table/ShopTable";

export const Shop = () => {
    const [pets, setPets] = useState([]);
    const [petsChanged, setPetsChanged] = useState(false);
    const classes = useStyles();

    const deletePetById = (id) => {
        deletePet(id).then(() => setPetsChanged(!petsChanged))
    }

    const changePetById = (id) => {
        const updatedPet = pets.find(pet => pet.id === id);
        editPet(id, updatedPet).then(() => setPetsChanged(!petsChanged));
    }

    useEffect(() => {
        getPets().then(
            pets => {
                pets.sort((pet_0, pet_1) => (pet_0.id > pet_1.id) ? 1 : ((pet_1.id > pet_0.id) ? -1 : 0));
                setPets(pets)
            }
        );
    }, [petsChanged]);

    return (
        <Grid
            container
            alignItems="center"
            justify="center"
            className={classes.root}
        >
            <ShopTable
                rows={pets}
                deletePetById={deletePetById}
                changePetById={changePetById}
            />
        </Grid>
    );
}
