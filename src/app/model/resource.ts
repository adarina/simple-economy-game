export class Resource {

    id: number;

    type: string;

    amount: number

    constructor(id: number, type: string, amount: number) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }
}
