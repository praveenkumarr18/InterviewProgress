import { NavLink } from 'react-router-dom';

const links = [
  { to: '/', label: 'Dashboard' },
  { to: '/companies', label: 'Companies' },
  { to: '/experiences', label: 'Experiences' },
  { to: '/questions', label: 'Questions' },
  { to: '/resume', label: 'Resume' },
  { to: '/feedback', label: 'Feedback' },
];

export default function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="brand">
        <h1>Interview Compass</h1>
        <p>A Placement Journey Management System for Students.</p>
      </div>
      <nav className="nav-group">
        {links.map((link) => (
          <NavLink key={link.to} to={link.to} end className={({ isActive }) => `nav-link${isActive ? ' active' : ''}`}>
            {link.label}
          </NavLink>
        ))}
      </nav>
    </aside>
  );
}
