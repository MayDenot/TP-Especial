// ========================================
// Script de Población de MongoDB
// TP-Especial - Sistema de Alquiler de Monopatines
// Colección: electric_scooters
// ========================================

// Conectar a la base de datos
use scooters_db;

// Limpiar la colección si existe (opcional)
// db.electric_scooters.drop();

// ========================================
// Insertar Monopatines Eléctricos
// ========================================

db.electric_scooters.insertMany([
  // SCOOTER-001 - Actualmente en viaje (pausado)
  {
    _id: "SCOOTER-001",
    longitud: -59.1333,
    latitud: -37.3215,
    habilitado: true,
    bateria: 75,
    tiempoDeUso: 14400000, // 4 horas en milisegundos
    kilometrosRecorridos: 45.5,
    enParada: false,
    codigoQR: "QR-001-ABC123",
    estado: "OCUPADO",
    fechaAlta: new Date("2024-01-10T00:00:00Z"),
    ultimaActualizacion: new Date("2024-11-07T18:00:00Z"),
    idParadaActual: 1,
    tiempoEnMovimiento: 12600000, // 3.5 horas
    tiempoEnPausa: 1800000, // 30 minutos
    ubicacion: {
      type: "Point",
      coordinates: [-59.1333, -37.3215]
    }
  },

  // SCOOTER-002 - Disponible en parada 2
  {
    _id: "SCOOTER-002",
    longitud: -59.1389,
    latitud: -37.3156,
    habilitado: true,
    bateria: 92,
    tiempoDeUso: 18000000, // 5 horas
    kilometrosRecorridos: 67.3,
    enParada: true,
    codigoQR: "QR-002-DEF456",
    estado: "DISPONIBLE",
    fechaAlta: new Date("2024-01-15T00:00:00Z"),
    ultimaActualizacion: new Date("2024-11-07T17:45:00Z"),
    idParadaActual: 2,
    tiempoEnMovimiento: 16200000, // 4.5 horas
    tiempoEnPausa: 1800000, // 30 minutos
    ubicacion: {
      type: "Point",
      coordinates: [-59.1389, -37.3156]
    }
  },

  // SCOOTER-003 - Disponible en parada 3
  {
    _id: "SCOOTER-003",
    longitud: -59.1421,
    latitud: -37.3289,
    habilitado: true,
    bateria: 68,
    tiempoDeUso: 21600000, // 6 horas
    kilometrosRecorridos: 89.2,
    enParada: true,
    codigoQR: "QR-003-GHI789",
    estado: "DISPONIBLE",
    fechaAlta: new Date("2024-01-20T00:00:00Z"),
    ultimaActualizacion: new Date("2024-11-07T17:30:00Z"),
    idParadaActual: 3,
    tiempoEnMovimiento: 19800000, // 5.5 horas
    tiempoEnPausa: 1800000, // 30 minutos
    ubicacion: {
      type: "Point",
      coordinates: [-59.1421, -37.3289]
    }
  },

  // SCOOTER-004 - Disponible en parada 4
  {
    _id: "SCOOTER-004",
    longitud: -59.1267,
    latitud: -37.3298,
    habilitado: true,
    bateria: 85,
    tiempoDeUso: 10800000, // 3 horas
    kilometrosRecorridos: 34.8,
    enParada: true,
    codigoQR: "QR-004-JKL012",
    estado: "DISPONIBLE",
    fechaAlta: new Date("2024-02-01T00:00:00Z"),
    ultimaActualizacion: new Date("2024-11-07T16:00:00Z"),
    idParadaActual: 4,
    tiempoEnMovimiento: 9000000, // 2.5 horas
    tiempoEnPausa: 1800000, // 30 minutos
    ubicacion: {
      type: "Point",
      coordinates: [-59.1267, -37.3298]
    }
  },

  // SCOOTER-005 - Disponible en parada 5
  {
    _id: "SCOOTER-005",
    longitud: -59.1445,
    latitud: -37.3178,
    habilitado: true,
    bateria: 95,
    tiempoDeUso: 7200000, // 2 horas
    kilometrosRecorridos: 23.5,
    enParada: true,
    codigoQR: "QR-005-MNO345",
    estado: "DISPONIBLE",
    fechaAlta: new Date("2024-02-05T00:00:00Z"),
    ultimaActualizacion: new Date("2024-11-07T15:35:00Z"),
    idParadaActual: 5,
    tiempoEnMovimiento: 6300000, // 1.75 horas
    tiempoEnPausa: 900000, // 15 minutos
    ubicacion: {
      type: "Point",
      coordinates: [-59.1445, -37.3178]
    }
  },

  // SCOOTER-006 - Disponible en parada 6
  {
    _id: "SCOOTER-006",
    longitud: -59.1301,
    latitud: -37.3334,
    habilitado: true,
    bateria: 78,
    tiempoDeUso: 16200000, // 4.5 horas
    kilometrosRecorridos: 56.7,
    enParada: true,
    codigoQR: "QR-006-PQR678",
    estado: "DISPONIBLE",
    fechaAlta: new Date("2024-02-10T00:00:00Z"),
    ultimaActualizacion: new Date("2024-11-07T17:15:00Z"),
    idParadaActual: 6,
    tiempoEnMovimiento: 14400000, // 4 horas
    tiempoEnPausa: 1800000, // 30 minutos
    ubicacion: {
      type: "Point",
      coordinates: [-59.1301, -37.3334]
    }
  },

  // SCOOTER-007 - Disponible en parada 7
  {
    _id: "SCOOTER-007",
    longitud: -59.1556,
    latitud: -37.3267,
    habilitado: true,
    bateria: 88,
    tiempoDeUso: 12600000, // 3.5 horas
    kilometrosRecorridos: 41.2,
    enParada: true,
    codigoQR: "QR-007-STU901",
    estado: "DISPONIBLE",
    fechaAlta: new Date("2024-02-15T00:00:00Z"),
    ultimaActualizacion: new Date("2024-11-07T15:50:00Z"),
    idParadaActual: 7,
    tiempoEnMovimiento: 10800000, // 3 horas
    tiempoEnPausa: 1800000, // 30 minutos
    ubicacion: {
      type: "Point",
      coordinates: [-59.1556, -37.3267]
    }
  },

  // SCOOTER-008 - Disponible en parada 8
  {
    _id: "SCOOTER-008",
    longitud: -59.1312,
    latitud: -37.3401,
    habilitado: true,
    bateria: 72,
    tiempoDeUso: 19800000, // 5.5 horas
    kilometrosRecorridos: 73.9,
    enParada: true,
    codigoQR: "QR-008-VWX234",
    estado: "DISPONIBLE",
    fechaAlta: new Date("2024-02-20T00:00:00Z"),
    ultimaActualizacion: new Date("2024-11-07T18:45:00Z"),
    idParadaActual: 8,
    tiempoEnMovimiento: 18000000, // 5 horas
    tiempoEnPausa: 1800000, // 30 minutos
    ubicacion: {
      type: "Point",
      coordinates: [-59.1312, -37.3401]
    }
  },

  // SCOOTER-009 - Disponible en parada 9
  {
    _id: "SCOOTER-009",
    longitud: -59.1478,
    latitud: -37.3245,
    habilitado: true,
    bateria: 91,
    tiempoDeUso: 9000000, // 2.5 horas
    kilometrosRecorridos: 29.4,
    enParada: true,
    codigoQR: "QR-009-YZA567",
    estado: "DISPONIBLE",
    fechaAlta: new Date("2024-03-01T00:00:00Z"),
    ultimaActualizacion: new Date("2024-11-07T14:15:00Z"),
    idParadaActual: 9,
    tiempoEnMovimiento: 7200000, // 2 horas
    tiempoEnPausa: 1800000, // 30 minutos
    ubicacion: {
      type: "Point",
      coordinates: [-59.1478, -37.3245]
    }
  },

  // SCOOTER-010 - Disponible en parada 10
  {
    _id: "SCOOTER-010",
    longitud: -59.1190,
    latitud: -37.3289,
    habilitado: true,
    bateria: 83,
    tiempoDeUso: 14400000, // 4 horas
    kilometrosRecorridos: 52.1,
    enParada: true,
    codigoQR: "QR-010-BCD890",
    estado: "DISPONIBLE",
    fechaAlta: new Date("2024-03-05T00:00:00Z"),
    ultimaActualizacion: new Date("2024-11-07T16:20:00Z"),
    idParadaActual: 10,
    tiempoEnMovimiento: 12600000, // 3.5 horas
    tiempoEnPausa: 1800000, // 30 minutos
    ubicacion: {
      type: "Point",
      coordinates: [-59.1190, -37.3289]
    }
  },

  // SCOOTER-011 - En reparación (deshabilitado)
  {
    _id: "SCOOTER-011",
    longitud: -59.1333,
    latitud: -37.3215,
    habilitado: false,
    bateria: 15,
    tiempoDeUso: 36000000, // 10 horas
    kilometrosRecorridos: 145.8,
    enParada: true,
    codigoQR: "QR-011-EFG123",
    estado: "REPARADO",
    fechaAlta: new Date("2024-03-10T00:00:00Z"),
    ultimaActualizacion: new Date("2024-11-06T10:00:00Z"),
    idParadaActual: 1,
    tiempoEnMovimiento: 32400000, // 9 horas
    tiempoEnPausa: 3600000, // 1 hora
    ubicacion: {
      type: "Point",
      coordinates: [-59.1333, -37.3215]
    }
  },

  // SCOOTER-012 - Disponible con batería baja
  {
    _id: "SCOOTER-012",
    longitud: -59.1389,
    latitud: -37.3156,
    habilitado: true,
    bateria: 18,
    tiempoDeUso: 32400000, // 9 horas
    kilometrosRecorridos: 126.3,
    enParada: true,
    codigoQR: "QR-012-HIJ456",
    estado: "DISPONIBLE",
    fechaAlta: new Date("2024-03-15T00:00:00Z"),
    ultimaActualizacion: new Date("2024-11-07T18:50:00Z"),
    idParadaActual: 2,
    tiempoEnMovimiento: 28800000, // 8 horas
    tiempoEnPausa: 3600000, // 1 hora
    ubicacion: {
      type: "Point",
      coordinates: [-59.1389, -37.3156]
    }
  },

  // SCOOTER-013 - Nuevo, sin uso
  {
    _id: "SCOOTER-013",
    longitud: -59.1421,
    latitud: -37.3289,
    habilitado: true,
    bateria: 100,
    tiempoDeUso: 0,
    kilometrosRecorridos: 0.0,
    enParada: true,
    codigoQR: "QR-013-KLM789",
    estado: "DISPONIBLE",
    fechaAlta: new Date("2024-11-01T00:00:00Z"),
    ultimaActualizacion: new Date("2024-11-01T00:00:00Z"),
    idParadaActual: 3,
    tiempoEnMovimiento: 0,
    tiempoEnPausa: 0,
    ubicacion: {
      type: "Point",
      coordinates: [-59.1421, -37.3289]
    }
  },

  // SCOOTER-014 - Disponible, alto uso
  {
    _id: "SCOOTER-014",
    longitud: -59.1267,
    latitud: -37.3298,
    habilitado: true,
    bateria: 65,
    tiempoDeUso: 43200000, // 12 horas
    kilometrosRecorridos: 189.5,
    enParada: true,
    codigoQR: "QR-014-NOP012",
    estado: "DISPONIBLE",
    fechaAlta: new Date("2024-01-05T00:00:00Z"),
    ultimaActualizacion: new Date("2024-11-07T12:30:00Z"),
    idParadaActual: 4,
    tiempoEnMovimiento: 39600000, // 11 horas
    tiempoEnPausa: 3600000, // 1 hora
    ubicacion: {
      type: "Point",
      coordinates: [-59.1267, -37.3298]
    }
  },

  // SCOOTER-015 - En reparación
  {
    _id: "SCOOTER-015",
    longitud: -59.1445,
    latitud: -37.3178,
    habilitado: false,
    bateria: 42,
    tiempoDeUso: 28800000, // 8 horas
    kilometrosRecorridos: 112.7,
    enParada: true,
    codigoQR: "QR-015-QRS345",
    estado: "REPARADO",
    fechaAlta: new Date("2024-01-25T00:00:00Z"),
    ultimaActualizacion: new Date("2024-11-05T14:00:00Z"),
    idParadaActual: 5,
    tiempoEnMovimiento: 25200000, // 7 horas
    tiempoEnPausa: 3600000, // 1 hora
    ubicacion: {
      type: "Point",
      coordinates: [-59.1445, -37.3178]
    }
  }
]);

// ========================================
// Crear índice geoespacial (2dsphere)
// ========================================
db.electric_scooters.createIndex({ ubicacion: "2dsphere" });

// ========================================
// Verificación de datos insertados
// ========================================
print("\n========================================");
print("Verificación de datos insertados");
print("========================================");

var totalScooters = db.electric_scooters.countDocuments();
print("Total de monopatines insertados: " + totalScooters);

var disponibles = db.electric_scooters.countDocuments({ estado: "DISPONIBLE" });
print("Monopatines DISPONIBLES: " + disponibles);

var ocupados = db.electric_scooters.countDocuments({ estado: "OCUPADO" });
print("Monopatines OCUPADOS: " + ocupados);

var reparados = db.electric_scooters.countDocuments({ estado: "REPARADO" });
print("Monopatines en REPARACIÓN: " + reparados);

var enParada = db.electric_scooters.countDocuments({ enParada: true });
print("Monopatines en parada: " + enParada);

var habilitados = db.electric_scooters.countDocuments({ habilitado: true });
print("Monopatines habilitados: " + habilitados);

print("\n========================================");
print("¡Base de datos MongoDB poblada exitosamente!");
print("========================================\n");

// Mostrar algunos monopatines de ejemplo
print("Ejemplos de monopatines insertados:");
db.electric_scooters.find().limit(3).forEach(function(scooter) {
  print("\n- ID: " + scooter.id);
  print("  Estado: " + scooter.estado);
  print("  Batería: " + scooter.bateria + "%");
  print("  Km recorridos: " + scooter.kilometrosRecorridos);
  print("  Ubicación: [" + scooter.ubicacion.coordinates[0] + ", " + scooter.ubicacion.coordinates[1] + "]");
});
