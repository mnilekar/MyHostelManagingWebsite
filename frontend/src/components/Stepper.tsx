interface Props { current: number }
const steps = ['Basic Details', 'Floor Plan', 'Facilities'];
export default function Stepper({ current }: Props) {
  return <div className="wizard-stepper">{steps.map((step, i) => <div key={step} className={`step ${current===i+1?'active':''}`}>Step {i+1}: {step}</div>)}</div>;
}
