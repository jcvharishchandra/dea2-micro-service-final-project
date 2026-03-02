import api from "@/lib/axios";

const BASE = "/api/equipments";

export const getAllEquipments = () => api.get(BASE);

export const getEquipmentById = (id) => api.get(`${BASE}/${id}`);

export const createEquipment = (data) => api.post(BASE, data);

export const updateEquipment = (id, data) => api.put(`${BASE}/${id}`, data);

export const deleteEquipment = (id) => api.delete(`${BASE}/${id}`);
