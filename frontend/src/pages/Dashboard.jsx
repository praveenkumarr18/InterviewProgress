import Navbar from '../components/Navbar';
import DashboardCard from '../components/DashboardCard';

const cards = [
  {
    title: 'Real data only',
    description: 'Dashboard metrics will come from backend queries instead of hardcoded numbers.',
  },
  {
    title: 'Nested workflows',
    description: 'Companies lead to experiences, rounds, questions, feedback, and resume versions.',
  },
  {
    title: 'Frontend first-class',
    description: 'The UI is a clean route structure now, not a single placeholder screen.',
  },
];

export default function Dashboard() {
  return (
    <>
      <Navbar />
      <h3 className="section-title">Workspace overview</h3>
      <section className="grid">
        {cards.map((card) => (
          <DashboardCard key={card.title} title={card.title} description={card.description} />
        ))}
      </section>
    </>
  );
}
