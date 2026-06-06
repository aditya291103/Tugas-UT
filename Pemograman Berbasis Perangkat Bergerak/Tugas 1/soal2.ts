let nim: string = "053389678";

let awal: number = parseInt(nim.slice(-2));

let beda: number = parseInt(nim[nim.length - 3]) + 1;

let hasil: number[] = [];

for (let i = 0; i < 10; i++) {
    hasil.push(awal + (i * beda));
}

console.log("NIM:", nim);
console.log("Awal:", awal);
console.log("Beda:", beda);
console.log(hasil.join(", "));

export {};