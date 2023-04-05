import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import InitPage from "./pages/InitPage";
import MainPage from "./pages/MainPage";
import MyPage from "./pages/MyPage";
import AddReview from "./pages/AddReview";
import Preference from "./pages/Preference";
import Recommend from "./pages/Recommend";
import Perfume from "./pages/Perfume";
import SurveyPage from "./pages/SurveyPage";
import GuidePage from "./pages/Guide";
import LoginPage from "./pages/LoginPage";
import SurveyResultPage from "./pages/SurveyResultPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<InitPage />} />
        <Route path="/survey/result" element={<SurveyResultPage />} />
        <Route path="/survey/:num" element={<SurveyPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/mainpage" element={<MainPage />} />
        <Route path="/mypage" element={<MyPage />} />
        <Route path="/mypage/addreview" element={<AddReview />} />
        <Route path="/preference" element={<Preference />} />
        <Route path="/recommend" element={<Recommend />} />
        <Route path="/perfume/:no" element={<Perfume />} />
        <Route path="/guide" element={<GuidePage />} />
      </Routes>
    </Router>
  );
}

export default App;
