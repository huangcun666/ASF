package example.com.asf;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private TextView tv;
    private SensorManager sm;
    private float[] gtavity=new float[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv= (TextView) findViewById(R.id.tv);
        sm= (SensorManager) getSystemService(SENSOR_SERVICE);
       /*   List<Sensor> ls=sm.getSensorList(Sensor.TYPE_ALL);
            for (Sensor sensor:ls){
               tv.append(sensor.getName()+"\n");
            }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onrsume");
        sm.registerListener
                (this,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),sm.SENSOR_DELAY_UI);
        sm.registerListener
                (this,sm.getDefaultSensor(Sensor.TYPE_GRAVITY),sm.SENSOR_DELAY_UI);
        sm.registerListener
                (this,sm.getDefaultSensor(Sensor.TYPE_PROXIMITY),sm.SENSOR_DELAY_UI);
             }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
            switch (sensorEvent.sensor.getType()){
                case Sensor.TYPE_ACCELEROMETER:
                    final float alpha=(float)0.8;
                    gtavity[0]=alpha*gtavity[0]+(1-alpha)*sensorEvent.values[0];
                    gtavity[1]=alpha*gtavity[1]+(1-alpha)*sensorEvent.values[1];
                    gtavity[2]=alpha*gtavity[2]+(1-alpha)*sensorEvent.values[2];
                    String s="加速度\n"+"X:"+(sensorEvent.values[0]-gtavity[0])+"\n"+
                            "Y:"+(sensorEvent.values[1]-gtavity[1])+"\n"+"Z:"+(sensorEvent.values[2]-gtavity[2]);
                   // Log.d("z", String.valueOf(sensorEvent.values[2]-gtavity[2]));
                    tv.setText(s);
                    break;
                case Sensor.TYPE_GRAVITY:
                    gtavity[0]=sensorEvent.values[0];
                    gtavity[1]=sensorEvent.values[1];
                    gtavity[2]=sensorEvent.values[2];
                    break;
                case Sensor.TYPE_PROXIMITY:
                    setTitle(String.valueOf(sensorEvent.values[0]));
                    break;
            }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
