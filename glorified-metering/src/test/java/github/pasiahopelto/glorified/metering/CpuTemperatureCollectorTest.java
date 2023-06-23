package github.pasiahopelto.glorified.metering;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import github.pasiahopelto.glorified.metering.DbWriter.Type;
import github.pasiahopelto.glorified.metering.SpringConfig.ProcFileReaderFactory;

@ExtendWith(MockitoExtension.class)
public class CpuTemperatureCollectorTest {

    private static final String TEMPERATURE = "51400";

    private Reader reader = new StringReader(TEMPERATURE);
    
    @Mock
    private ProcFileReaderFactory readerFactory;

    @Mock
    private DbWriter dbWriter;

    @InjectMocks
	private CpuTemperatureCollector collector;

	@Test
	public void failsGracefullyWhenExceptionGetsThrown() throws IOException {
		when(readerFactory.reader()).thenThrow(new FileNotFoundException("TEST"));
		collector.storeCurrentCpuTemperature();
		verifyNoInteractions(dbWriter);
	}	

	@Test
	public void collectsCpuTemperature() throws IOException {
		when(readerFactory.reader()).thenReturn(reader);
		collector.storeCurrentCpuTemperature();
		verify(dbWriter, times(1)).writeTemperature(Type.CPU, 51.4f);
	}	
}
