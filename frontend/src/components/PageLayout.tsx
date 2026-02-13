import { ReactNode } from 'react';
import NavTabs from './NavTabs';

export default function PageLayout({ title, children }: { title: string; children: ReactNode }) {
  return (
    <main className="container">
      <h1>{title}</h1>
      <NavTabs />
      <section className="card">{children}</section>
    </main>
  );
}
