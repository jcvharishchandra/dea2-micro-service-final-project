"use client";

import React, { useEffect, useState } from "react";
import MoveToInboxIcon from "@mui/icons-material/MoveToInbox";
import AddIcon from "@mui/icons-material/Add";
import VisibilityOutlinedIcon from "@mui/icons-material/VisibilityOutlined";
import DeleteOutlineIcon from "@mui/icons-material/DeleteOutline";
import {
  Box,
  Typography,
  Button,
  Paper,
  Tabs,
  Tab,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Chip,
  Stack,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from "@mui/material";

import {
  getInboundShipments,
  getAllReceipts,
  getAllReceiptItems,
  getShipmentById,
  updateShipmentStatus,
  deleteShipment,
} from "@/services/inbound_service/inboundApi";

import ReceiveShipmentModal from "@/components/services/inbound_service/ReceiveShipmentModal";

export default function InboundServicePage() {
  const [tabValue, setTabValue] = useState(0);
  const [data, setData] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const [detailsOpen, setDetailsOpen] = useState(false);
  const [detailsLoading, setDetailsLoading] = useState(false);
  const [selectedShipment, setSelectedShipment] = useState(null);

  const fetchData = async () => {
    try {
      let res;
      if (tabValue === 0) res = await getInboundShipments();
      else if (tabValue === 1) res = await getAllReceipts();
      else res = await getAllReceiptItems();

      setData(res.data || []);
    } catch (err) {
      console.error("Fetch error:", err);
      setData([]);
    }
  };

  useEffect(() => {
    fetchData();
  }, [tabValue]);

  const handleStatusChange = async (id, currentStatus) => {
    let nextStatus = "COMPLETED";

    if (currentStatus === "PENDING") nextStatus = "RECEIVED";
    else if (currentStatus === "RECEIVED") nextStatus = "COMPLETED";
    else if (currentStatus === "COMPLETED") nextStatus = "COMPLETED";

    try {
      await updateShipmentStatus(id, nextStatus);
      await fetchData();

      if (selectedShipment?.id === id) {
        const res = await getShipmentById(id);
        setSelectedShipment(res.data);
      }
    } catch (err) {
      console.error(err);
    }
  };

  const handleDelete = async (id) => {
    const confirmed = window.confirm("Are you sure you want to delete this inbound record?");
    if (!confirmed) return;

    try {
      await deleteShipment(id);
      await fetchData();

      if (selectedShipment?.id === id) {
        setDetailsOpen(false);
        setSelectedShipment(null);
      }
    } catch (err) {
      console.error(err);
    }
  };

  const handleView = async (id) => {
    try {
      setDetailsLoading(true);
      setDetailsOpen(true);
      const res = await getShipmentById(id);
      setSelectedShipment(res.data);
    } catch (err) {
      console.error(err);
      setSelectedShipment(null);
    } finally {
      setDetailsLoading(false);
    }
  };

  return (
    <Box sx={{ p: 4 }}>
      <Box sx={{ display: "flex", justifyContent: "space-between", mb: 4 }}>
        <Box sx={{ display: "flex", alignItems: "center", gap: 1.5 }}>
          <MoveToInboxIcon sx={{ fontSize: 32, color: "#6366f1" }} />
          <Typography variant="h4" fontWeight={700}>
            Inbound Service
          </Typography>
        </Box>

        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={() => setIsModalOpen(true)}
          sx={{ bgcolor: "#6366f1", borderRadius: 2 }}
        >
          New Inbound
        </Button>
      </Box>

      <Tabs
        value={tabValue}
        onChange={(e, val) => setTabValue(val)}
        sx={{ mb: 3, "& .MuiTabs-indicator": { bgcolor: "#6366f1" } }}
      >
        <Tab label="Shipments" />
        <Tab label="Receipts (GRNs)" />
        <Tab label="Receipt Items" />
      </Tabs>

      <Paper
        elevation={0}
        sx={{
          borderRadius: 3,
          border: "1px solid #e2e8f0",
          overflow: "hidden",
        }}
      >
        <TableContainer>
          <Table>
            <TableHead sx={{ bgcolor: "#f8fafc" }}>
              <TableRow>
                {tabValue === 0 && (
                  <>
                    <TableCell>Supplier</TableCell>
                    <TableCell>Product</TableCell>
                    <TableCell>Status</TableCell>
                    <TableCell align="right">Action</TableCell>
                  </>
                )}
                {tabValue === 1 && (
                  <>
                    <TableCell>GRN Number</TableCell>
                    <TableCell>Date</TableCell>
                    <TableCell>Status</TableCell>
                  </>
                )}
                {tabValue === 2 && (
                  <>
                    <TableCell>Item ID</TableCell>
                    <TableCell>Product Name</TableCell>
                    <TableCell>Qty Received</TableCell>
                    <TableCell>Quality</TableCell>
                  </>
                )}
              </TableRow>
            </TableHead>

            <TableBody>
              {data.map((row) => (
                <TableRow key={row.id} hover>
                  {tabValue === 0 && (
                    <>
                      <TableCell>{row.supplierName}</TableCell>
                      <TableCell>{row.productName}</TableCell>
                      <TableCell>
                        <Chip label={row.status} size="small" />
                      </TableCell>
                      <TableCell align="right">
                        <Stack
                          direction="row"
                          spacing={1}
                          justifyContent="flex-end"
                        >
                          <Button
                            size="small"
                            onClick={() => handleView(row.id)}
                            startIcon={<VisibilityOutlinedIcon fontSize="small" />}
                          >
                            View
                          </Button>
                          <Button
                            size="small"
                            onClick={() => handleStatusChange(row.id, row.status)}
                          >
                            Update
                          </Button>
                          <Button
                            size="small"
                            color="error"
                            onClick={() => handleDelete(row.id)}
                            startIcon={<DeleteOutlineIcon fontSize="small" />}
                          >
                            Delete
                          </Button>
                        </Stack>
                      </TableCell>
                    </>
                  )}

                  {tabValue === 1 && (
                    <>
                      <TableCell sx={{ color: "#6366f1", fontWeight: 600 }}>
                        {row.grnNumber || row.receiptNumber}
                      </TableCell>
                      <TableCell>
                        {row.receiptDate || row.receivedAt
                          ? new Date(row.receiptDate || row.receivedAt).toLocaleDateString()
                          : "-"}
                      </TableCell>
                      <TableCell>
                        <Chip label={row.status} color="success" size="small" />
                      </TableCell>
                    </>
                  )}

                  {tabValue === 2 && (
                    <>
                      <TableCell>#{row.id}</TableCell>
                      <TableCell>{row.productName || `ID: ${row.productId}`}</TableCell>
                      <TableCell>{row.quantityReceived}</TableCell>
                      <TableCell>
                        <Chip
                          label={row.qualityStatus || row.condition || "PENDING"}
                          size="small"
                          variant="outlined"
                        />
                      </TableCell>
                    </>
                  )}
                </TableRow>
              ))}

              {data.length === 0 && (
                <TableRow>
                  <TableCell colSpan={tabValue === 0 ? 4 : 3} align="center">
                    No data found.
                  </TableCell>
                </TableRow>
              )}
            </TableBody>
          </Table>
        </TableContainer>
      </Paper>

      <ReceiveShipmentModal
        open={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onRefresh={fetchData}
      />

      <Dialog
        open={detailsOpen}
        onClose={() => setDetailsOpen(false)}
        fullWidth
        maxWidth="sm"
      >
        <DialogTitle sx={{ fontWeight: 700 }}>
          Shipment Details
        </DialogTitle>
        <DialogContent>
          {detailsLoading ? (
            <Typography sx={{ py: 2 }}>Loading...</Typography>
          ) : !selectedShipment ? (
            <Typography sx={{ py: 2 }}>No data found.</Typography>
          ) : (
            <Box sx={{ display: "flex", flexDirection: "column", gap: 1.5, mt: 1 }}>
              <Typography><b>ID:</b> {selectedShipment.id}</Typography>
              <Typography><b>Supplier:</b> {selectedShipment.supplierName}</Typography>
              <Typography><b>Product:</b> {selectedShipment.productName}</Typography>
              <Typography><b>Quantity:</b> {selectedShipment.quantity}</Typography>
              <Typography><b>Status:</b> {selectedShipment.status}</Typography>
            </Box>
          )}
        </DialogContent>
        <DialogActions sx={{ p: 2 }}>
          {selectedShipment && (
            <>
              <Button onClick={() => handleStatusChange(selectedShipment.id, selectedShipment.status)}>
                Update Status
              </Button>
              <Button color="error" onClick={() => handleDelete(selectedShipment.id)}>
                Delete
              </Button>
            </>
          )}
          <Button onClick={() => setDetailsOpen(false)}>Close</Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
}