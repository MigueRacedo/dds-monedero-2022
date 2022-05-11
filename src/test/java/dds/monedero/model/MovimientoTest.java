package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class MovimientoTest {
  private Movimiento movimiento;
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    movimiento = new Movimiento(LocalDate.of(2022, 5, 6),1000,true);
    cuenta = new Cuenta(1000);
  }

  @Test
  void ElMontoDelMovimientoEs1000() {
    assertEquals(movimiento.getMonto(),1000,0);
  }

  @Test
  void ElMovimientoEsDeUnaFechaCorrecta() {
    LocalDate fecha = LocalDate.of(2022, 5, 6);
    assertTrue(movimiento.esDeLaFecha(fecha));
  }

  @Test
  void ElMovimientoNoFueDepositadoAyer() {
    LocalDate ayer = LocalDate.now().plusDays(-1);
    assertFalse(movimiento.fueDepositado(ayer));
  }

  @Test
  void ElMontoDelMovimientoNoUnaExtraccion() {
    assertFalse(movimiento.fueExtraido(LocalDate.now()));
  }

  @Test
  void ElValorDeLaCuentaEs3000() {
    movimiento.agregateACuenta(cuenta);
    assertEquals(movimiento.calcularValor(cuenta),3000,0);
  }
}