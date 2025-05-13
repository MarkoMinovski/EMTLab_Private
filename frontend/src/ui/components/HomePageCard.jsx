import * as React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';

const HomePageCard = ({ type }) => {

    const cardString = "./src/assets/card_" + type + ".jpg";

    let cardText = "";
    let cardTitle = "";
    if (type === "country") {
        cardText = "We feature a variety of countries of origin from all over the globe."
        cardTitle = "Countries";
    } else if (type === "author") {
        cardText = "A vast collection of world-famous authors from all eras throughout human history."
        cardTitle = "Authors";
    } else if (type === "book") {
        cardText = "Classics, horror, sci-fi, fantasy - you name it, we probably have it."
        cardTitle = "Books";
    }

    return (
        <Card sx={{ maxWidth: 345 }}>
            <CardMedia
                sx={{ height: 140 }}
                image={cardString}
                title="green iguana"
            />
            <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                    {cardTitle}
                </Typography>
                <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                    {cardText}
                </Typography>
            </CardContent>
            <CardActions>
                <Button size="small" sx={{ color: "#bfbdbd" }}>Share</Button>
                <Button size="small" sx={{ color: "#bfbdbd" }}>Learn More</Button>
            </CardActions>
        </Card>
    );
}

export default HomePageCard;