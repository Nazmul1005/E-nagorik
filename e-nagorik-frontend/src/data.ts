export type Role = 'citizen' | 'officer' | 'mayor'
export type Status = 'Submitted' | 'In review' | 'Approved' | 'Completed' | 'Rejected'
export type Service = { id: string; title: string; bn: string; description: string; icon: string; color: string; eta: string }
export type Application = { id: string; service: string; applicant: string; submitted: string; status: Status; reference: string }

export const services: Service[] = [
  { id: 'birth', title: 'Birth Certificate', bn: 'জন্ম নিবন্ধন', description: 'Apply for a new birth certificate or a correction.', icon: '👶', color: '#e5f4f0', eta: '3–5 working days' },
  { id: 'trade', title: 'Trade License', bn: 'ট্রেড লাইসেন্স', description: 'Get your business trade license online.', icon: '🏪', color: '#fff2d7', eta: '5–7 working days' },
  { id: 'death', title: 'Death Certificate', bn: 'মৃত্যু নিবন্ধন', description: 'Register a death and download the certificate.', icon: '📜', color: '#f1eafb', eta: '3 working days' },
  { id: 'residency', title: 'Residency Certificate', bn: 'নাগরিকত্ব সনদ', description: 'Request a digital proof of residency.', icon: '🏠', color: '#e6f0fb', eta: '2–3 working days' },
  { id: 'complaint', title: 'Submit a Complaint', bn: 'অভিযোগ দাখিল', description: 'Report an issue in your ward or municipality.', icon: '📣', color: '#fde8e5', eta: 'Response within 48h' },
  { id: 'tax', title: 'Holding Tax', bn: 'হোল্ডিং ট্যাক্স', description: 'View and pay your municipal holding tax.', icon: '🧾', color: '#e7f5df', eta: 'Instant receipt' }
]

export const seedApplications: Application[] = [
  { id: 'APP-240981', reference: 'EN-2024-0981', service: 'Birth Certificate', applicant: 'Ayesha Rahman', submitted: '12 Jun 2024', status: 'Approved' },
  { id: 'APP-240977', reference: 'EN-2024-0977', service: 'Trade License', applicant: 'Rahim Uddin', submitted: '10 Jun 2024', status: 'In review' },
  { id: 'APP-240962', reference: 'EN-2024-0962', service: 'Residency Certificate', applicant: 'Nusrat Jahan', submitted: '04 Jun 2024', status: 'Completed' }
]
