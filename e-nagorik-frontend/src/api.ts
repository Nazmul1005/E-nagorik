import { seedApplications, services, type Application } from './data'

const mock = import.meta.env.VITE_USE_MOCK !== 'false'
const wait = (ms = 250) => new Promise(resolve => setTimeout(resolve, ms))
export const api = {
  async getServices() { await wait(); return services },
  async getApplications() { await wait(); return [...seedApplications] },
  async submitApplication(service: string, applicant: string): Promise<Application> {
    await wait()
    return { id: `APP-${Date.now().toString().slice(-6)}`, reference: `EN-2024-${Math.floor(1000 + Math.random() * 8999)}`, service, applicant, submitted: 'Today', status: 'Submitted' }
  },
  isMock: mock
}
