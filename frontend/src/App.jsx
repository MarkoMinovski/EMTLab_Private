// App.jsx
import { BrowserRouter, Routes, Route} from "react-router-dom";
import Footer from "./ui/components/layout/Footer.jsx";
import Layout from "./ui/components/Layout.jsx";
import HomePage from "./ui/pages/HomePage.jsx";
import CountriesPage from "./ui/pages/CountriesPage.jsx";
import CountryDetails from "./ui/components/countryComponents/CountryDetails.jsx";



// Main App Component
function App() {

    return (
        <BrowserRouter>

            <Routes>
                <Route path="/" element={<Layout/>}>
                    <Route index element={<HomePage/>}></Route>
                    <Route path="countries" element={<CountriesPage/>}/>
                    <Route path="countries/:id" element={<CountryDetails/>}/>
                </Route>
            </Routes>

            <Footer></Footer>
        </BrowserRouter>
    );
}

export default App;
