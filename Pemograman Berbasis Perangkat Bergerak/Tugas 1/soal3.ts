let nim: string = "053389678";

let batas: number = parseInt(nim.slice(-2)) + 10;

function isPrima(n: number): boolean {
    if (n < 2) return false;

    for (let i = 2; i <= Math.sqrt(n); i++) {
        if (n % i === 0) {
            return false;
        }
    }
    return true;
}

let prima: number[] = [];

for (let i = 1; i <= batas; i++) {
    if (isPrima(i)) {
        prima.push(i);
    }
}

console.log("NIM:", nim);
console.log("Batas:", batas);
console.log(prima.join(", "));
