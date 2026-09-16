export default function SimplePage({ title, description }) {
  return (
    <section className="card">
      <h3>{title}</h3>
      <p>{description}</p>
    </section>
  );
}
