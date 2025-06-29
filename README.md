# Trade-order-Simulator
A full-stack web application for monitoring and managing executed trades in a simulated stock trading system. This dashboard provides real-time visibility into trading activity and integrates with a Kafka-powered backend for event-driven architecture.

## 🔧 Features
📊 Trade History View: See all executed trades including trade ID, order IDs, price, quantity, and timestamp.

🔄 Refresh Button: Instantly reload the latest trades with a success message indicator.

🗑️ Delete Trade: Remove individual trade records from the database with confirmation prompt.

👥 Role Toggle: Switch between Buyer and Seller roles (for future role-based UI filtering).

📦 Backend Integration: Communicates with a Spring Boot API (/trades/allTrades, /trades/{id}).

✅ Clean UI: Built with React and styled using Tailwind CSS for responsive design.
