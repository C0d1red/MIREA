export const getPets = () =>
    fetch('pets', {
        method: "GET"
    })
        .then(response => response.json())
        .then(json => json['_embedded']['pets'])

export const deletePet = (id) =>
    fetch('pets/' + id, {
        method: "DELETE"
    })

export const editPet = (id, pet) =>
    fetch('pets/' + id, {
        method: "PATCH",
        headers: {"Content-type": "application/json; charset=UTF-8"},
        body: JSON.stringify(pet)
    })
