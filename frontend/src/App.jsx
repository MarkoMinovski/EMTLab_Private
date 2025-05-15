// App.jsx
import { BrowserRouter, Routes, Route} from "react-router-dom";
import Footer from "./ui/components/layout/Footer.jsx";
import Layout from "./ui/components/Layout.jsx";
import HomePage from "./ui/pages/HomePage.jsx";
import CountriesPage from "./ui/pages/CountriesPage.jsx";
import CountryDetails from "./ui/components/countryComponents/CountryDetails.jsx";
import AuthorPage from "./ui/pages/AuthorPage.jsx";
import AuthorDetails from "./ui/components/authorComponents/AuthorDetails.jsx";
import BooksPage from "./ui/pages/BooksPage.jsx";
import BookDetails from "./ui/components/bookComponents/BookDetails.jsx";



// Main App Component
function App() {

    return (
        <BrowserRouter>

            <Routes>
                <Route path="/" element={<Layout/>}>
                    <Route index element={<HomePage/>}></Route>
                    <Route path="countries" element={<CountriesPage/>}/>
                    <Route path="countries/:id" element={<CountryDetails/>}/>
                    <Route path="authors" element={<AuthorPage/>}/>
                    <Route path="authors/:id" element={<AuthorDetails/>}/>
                    <Route path="books" element={<BooksPage/>}/>
                    <Route path="books/:id" element={<BookDetails/>}/>
                </Route>
            </Routes>

            <Footer></Footer>
        </BrowserRouter>
    );
}

export default App;
