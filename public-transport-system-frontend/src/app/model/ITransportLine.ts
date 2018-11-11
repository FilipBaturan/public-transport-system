export interface ITransportLine {
    id: number,
    name: string,
    vehicle:{any},
    statsions: [any],
    schedule: [any],
    active: boolean,
    zone: {any}
}