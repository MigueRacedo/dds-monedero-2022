package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
  }

  @Test
  void DepositarSolo1500() {
    cuenta.depositar(1500);
    assertEquals(cuenta.getSaldo(),1500,0);
  }

  @Test
  void PonerMontoNegativoLanzaUnaExcepcion() {
    assertThrows(MontoNegativoException.class, () -> cuenta.depositar(-1500));
  }

  @Test
  void DepositarTresVeces3856() { //se testea la sumatoria de los depositos
    cuenta.depositar(1500);
    cuenta.depositar(456);
    cuenta.depositar(1900);
    assertEquals(cuenta.getSaldo(),3856,0);
  }

  @Test
  void MasDeTresDepositosLanzaUnaExcepcion() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
          cuenta.depositar(1500);
          cuenta.depositar(456);
          cuenta.depositar(1900);
          cuenta.depositar(245);
    });
  }

  @Test
  void ExtraerMasQueElSaldoLanzaUnaExcepcion() {
    assertThrows(SaldoMenorException.class, () -> {
          cuenta.setSaldo(90);
          cuenta.retirar(1001);
    });
  }

  @Test
  public void ExtraerMasDe1000LanzaUnaExcepcion() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(5000);
      cuenta.retirar(1001);
    });
  }

  @Test
  public void ExtraerMontoNegativoLanzaUnaExcepcion() {
    assertThrows(MontoNegativoException.class, () -> cuenta.retirar(-500));
  }

  @Test
  void LaCuentaTieneDosMovimientos() {
    cuenta.depositar(1500);
    cuenta.retirar(456);
    assertEquals(cuenta.getMovimientos().size(),2,0);
  }

  @Test
  void HoySeExtrageron500() {
    cuenta.depositar(1500);
    cuenta.retirar(500);
    assertEquals(cuenta.getMontoExtraidoA(LocalDate.now()),500,0);
  }

}