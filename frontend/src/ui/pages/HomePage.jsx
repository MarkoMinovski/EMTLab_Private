import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Navbar from "../components/layout/Navbar.jsx";
import Typography from "@mui/material/Typography";
import HomePageCard from "../components/HomePageCard.jsx";
import Grid from "@mui/material/Grid";

const HomePage = () => {
  return (

      <Box sx={{ flexGrow: 1 }}>
          <Container maxwidth="xl" sx={{ textAlign: "center" }}>

              <Typography variant="h2" sx={{ mt: 2, color: "#FFFFFF"}}>
                  MyLibrary
              </Typography>
              <Typography variant="h6" sx={{ m: 4, color: "#FFFFFF" }}>
                  The largest E-Library in Macedonia
              </Typography>
              <hr/>

              <Grid container spacing={2}>
                  <Grid size={4}>
                      <HomePageCard type={"country"}></HomePageCard>
                  </Grid>
                  <Grid size={4}>
                      <HomePageCard type={"author"}></HomePageCard>
                  </Grid>
                  <Grid size={4}>
                      <HomePageCard type={"book"}></HomePageCard>
                  </Grid>
              </Grid>

              <Typography variant="h2" sx={{ mt: 6, color: "#FFFFFF" }}>
                  Join us today.
              </Typography>
          </Container>
      </Box>
  )
};

export default HomePage;