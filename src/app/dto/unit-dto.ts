export class UnitDTO {

    id: number;
    
    type: string;

    amount: number;

    active: boolean;

    constructor(id: number, type: string, amount: number, active: boolean) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.active = active;
    }
}
