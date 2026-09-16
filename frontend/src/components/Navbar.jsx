export default function Navbar() {
  return (
    <header className="hero">
      <p className="eyebrow">Placement journey workspace</p>
      <h2>Track companies, rounds, questions, and improvement in one place.</h2>
      <p>
        The frontend is only a presentation layer. It will call Spring Boot APIs through Axios and render real user data
        from the backend.
      </p>
      <div className="hero-actions">
        <a className="button primary" href="/companies">Add Company</a>
        <a className="button" href="/resume">Upload Resume</a>
      </div>
    </header>
  );
}
