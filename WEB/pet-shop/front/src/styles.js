import {makeStyles} from '@material-ui/core/styles';

const styles = () => ({
    root: {
        minHeight: '100vh'
    },
    card: {
        width: '80%',
    },
    idCell: {
        width: '5%'
    },
    textCell: {
        width: '30%'
    },
    buttonCell: {
        width: '5%'
    },
    tableRow: {
        height: '100px'
    },
    tableBody: {
        height: '500px'
    }
});

export const useStyles = makeStyles(styles, { index: 1, name: 'Default' });
