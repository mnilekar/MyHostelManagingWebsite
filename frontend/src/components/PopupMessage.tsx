import { useEffect } from 'react';

type PopupMessageProps = {
  message: string;
  onClose: () => void;
};

export default function PopupMessage({ message, onClose }: PopupMessageProps) {
  useEffect(() => {
    const timer = window.setTimeout(onClose, 2200);
    return () => window.clearTimeout(timer);
  }, [onClose]);

  return (
    <div className="popup-overlay" role="presentation" onClick={onClose}>
      <div className="popup-message" role="alertdialog" onClick={(e) => e.stopPropagation()}>
        <p>{message}</p>
        <button type="button" onClick={onClose}>Okay</button>
      </div>
    </div>
  );
}
