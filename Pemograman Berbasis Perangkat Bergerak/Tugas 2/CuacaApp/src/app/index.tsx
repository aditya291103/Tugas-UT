import React, { useEffect, useState} from "react";
import { View, Text, ScrollView } from 'react-native';

export default function HomeScreen() {
  const [dataCuaca, setDataCuaca] = useState<any[]>([]);

  useEffect(() => {
    ambilData();
  }, []);

  const ambilData = async () => {
    const response = await fetch(
      'https://api.open-meteo.com/v1/forecast?latitude=-6.2&longitude=106.8&hourly=temperature_2m'
    );

    const data = await response.json();

    let hasil = [];

    for (let i = 0; i < data.hourly.time.length; i++) {
      hasil.push({
        time: data.hourly.time[i],
        temperature: data.hourly.temperature_2m[i],
      });
    }

    setDataCuaca(hasil);
  };

  return (
    <ScrollView
    style={{
      flex: 1,
      backgroundColor: '#fbfcfc',
      padding: 20,
      marginTop: 40,
    }}
    >
      <Text
      style={{
        fontSize: 24,
        fontWeight: 'bold',
        color: '#1d40bc',
        marginBottom: 15,
        textAlign: 'center',
      }}
    >
      Data Cuaca Jakarta
      </Text>

      {dataCuaca.map((item, index) => (
        <View
        key={index}
        style={{
          backgroundColor: 'white',
          padding: 12,
          borderRadius: 8,
          marginBottom: 10,
          borderLeftWidth: 5,
          borderLeftColor: '#1d40bc',
        }}
        >
          <Text
          style={{
            color: '#8a8e9b',
            fontSize: 12,
          }}
        >
          {item.time}
          </Text>

          <Text
          style={{
            color: '#111816',
            fontSize: 18,
            fontWeight: 'bold',
            marginTop: 3,
          }}
          >
           🌡 {item.temperature} °C
          </Text>
        </View>
      ))}

    </ScrollView>
  )
}