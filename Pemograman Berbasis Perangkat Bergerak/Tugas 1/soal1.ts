let nim: string = "053389678";

let tinggi: number = parseInt(nim[nim.length - 1]);

for (let i = 1; i <= tinggi; i++) {
    let baris: string = "";

    for (let j = 1; j <= i; j++) {
        baris += j + " ";
    }
    console.log(baris)
}

export {};