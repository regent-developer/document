# Android获取已配对的蓝牙列表和已连接蓝牙设备

```java
private void getPairBLEAndConnectBLE() {
    BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();

    if (defaultAdapter != null) {
        //得到已配对的设备列表
        Set<BluetoothDevice> devices = defaultAdapter.getBondedDevices();

        for (BluetoothDevice bluetoothDevice : devices) {
            boolean isConnect = false;

            try {
                //获取当前连接的蓝牙信息
                isConnect = (boolean) bluetoothDevice.getClass().getMethod("isConnected").invoke(bluetoothDevice);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }

            if (isConnect) {
                Log.d("markTest", "device2=" + bluetoothDevice.getAddress());
            }
            Log.d("markTest", "device1=" + bluetoothDevice.getName());
        }
    }
}
```