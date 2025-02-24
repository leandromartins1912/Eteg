import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import ClientesPage from "../presentation/pages/ClientesPage";

const RoutesConfig = () => {
  return (
    <Router>
      <Routes>
        <Route path="/clientes" element={<ClientesPage />} />
      </Routes>
    </Router>
  );
};

export default RoutesConfig;
