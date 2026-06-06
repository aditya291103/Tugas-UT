import { useEffect, useState } from "react";
import { View, Text, FlatList,StyleSheet } from "react-native";

export default function HomeScreen() {
  const [dataCrypto, setDataCrypto] = useState<any[]>([]);

  useEffect(() => {
    ambilData();
  }, []);

  const ambilData = async () => {
    try {
      const response = await fetch(
        "https://api.coinlore.net/api/tickers/"
      );

      const json = await response.json();

      setDataCrypto(json.data);
    } catch (error) {
      console.log(error);
    }
  };

  const renderItem = ({ item }: any) => (
    <View style={styles.card}>
      <View style={styles.row}>
        <View style={styles.column}>
          <Text style={styles.label}>Rank</Text>
          <Text style={styles.value}>{item.rank}</Text>
        </View>

        <View style={styles.column}>
          <Text style={styles.label}>{item.name}</Text>
          <Text style={styles.symbol}>{item.symbol}</Text>
        </View>

        <View style={styles.column}>
          <Text style={styles.label}>USD</Text>
          <Text style={styles.price}>
            {parseFloat(item.price_usd).toFixed(2)}
          </Text>
        </View>
      </View>
    </View>
  );

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Data CryptoCurrency</Text>

      <FlatList data={dataCrypto}
      keyExtractor={(item) => item.id}
      renderItem={renderItem}/>
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#f2f2f2",
    padding: 10,
    paddingTop: 60,
  },

  title: {
    fontSize: 24,
    fontWeight: "bold",
    textAlign: "center",
    marginVertical: 15,
    color: "#1f236e"
  },

  card: {
    backgroundColor: "#ebecca",
    borderWidth: 1,
    borderColor: "#cdbb78",
    padding: 12,
    marginBottom: 8,
    borderRadius: 6,
  },

  row: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
  },

  column: {
    flex: 1,
  },

  label: {
    fontSize: 12,
    color: "#636161",
  },

  value: {
    fontSize: 24,
    fontWeight: "bold",
  },

  symbol: {
    fontSize: 26,
    fontWeight: "bold",
  },

  price: {
    fontSize: 22,
    fontWeight: "bold",
  },
})