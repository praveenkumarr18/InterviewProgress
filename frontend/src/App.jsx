import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Sidebar from './components/Sidebar';
import Dashboard from './pages/Dashboard';
import Companies from './pages/Companies';
import Experiences from './pages/Experiences';
import Questions from './pages/Questions';
import ResumePage from './pages/ResumePage';
import Feedback from './pages/Feedback';
import './styles/app.css';

function AppLayout({ children }) {
  return (
    <div className="app-shell">
      <Sidebar />
      <main className="content">{children}</main>
    </div>
  );
}

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={
            <AppLayout>
              <Dashboard />
            </AppLayout>
          }
        />
        <Route
          path="/companies"
          element={
            <AppLayout>
              <Companies />
            </AppLayout>
          }
        />
        <Route
          path="/experiences"
          element={
            <AppLayout>
              <Experiences />
            </AppLayout>
          }
        />
        <Route
          path="/questions"
          element={
            <AppLayout>
              <Questions />
            </AppLayout>
          }
        />
        <Route
          path="/resume"
          element={
            <AppLayout>
              <ResumePage />
            </AppLayout>
          }
        />
        <Route
          path="/feedback"
          element={
            <AppLayout>
              <Feedback />
            </AppLayout>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}
